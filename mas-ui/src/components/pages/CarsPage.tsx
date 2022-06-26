import {
    Box, Button,
    Checkbox,
    Divider,
    IconButton,
    InputBase,
    List, ListItem, ListItemButton, ListItemIcon, ListItemText, Modal, Paper,
    Stack, Switch, TextField, Tooltip
} from '@mui/material';
import React, {useEffect, useRef, useState} from 'react';
import {
    ArrowBack,
    Discount,
    DiscountOutlined,
    Favorite,
    FavoriteBorder,
    FavoriteBorderOutlined
} from "@mui/icons-material";
import {NavigateFunction, Params, useNavigate, useParams} from "react-router-dom";
import {Car} from "../../models/Car";
import axios from "axios";
import {Offer} from "../../models/Offer";
import {useSnackbar} from "notistack";

export {CarsPage}

function not(a: Car[], b: Car[]) {
    return a.filter((value) => b.indexOf(value) === -1);
}

function intersect(a: Car[], b: Car[]) {
    return a.filter((value) => b.indexOf(value) !== -1);
}

function CarsPage() {

    const navigate: NavigateFunction = useNavigate()
    const params: Params = useParams()

    const [cars, setCars] = useState<Car[]>([])
    const [checked, setChecked] = React.useState<Car[]>([]);
    const [left, setLeft] = React.useState<Car[]>([]);
    const [right, setRight] = React.useState<Car[]>([]);
    const [open, setOpen] = React.useState<boolean>(false);

    const [discount, setDiscount] = useState<number>(0);
    const [discountSwitch, setDiscountSwitch] = useState<boolean>(false)

    const { enqueueSnackbar } = useSnackbar();

    useEffect(() => {
        const fetchData = async () => {
            const response = await axios.get<Car[]>("http://localhost:8080/api/cars")
            const data = response.data

            setCars(data)
            setLeft(data)
        }

        fetchData().catch(console.error)
    }, [])

    const leftChecked = intersect(checked, left);
    const rightChecked = intersect(checked, right);

    const handleToggle = (value: Car) => () => {
        const currentIndex = checked.indexOf(value);
        const newChecked = [...checked];

        if (currentIndex === -1) {
            newChecked.push(value);
        } else {
            newChecked.splice(currentIndex, 1);
        }

        setChecked(newChecked);
    };

    const handleAllRight = () => {
        setRight(right.concat(left));
        setLeft([]);
    };

    const handleCheckedRight = () => {
        setRight(right.concat(leftChecked));
        setLeft(not(left, leftChecked));
        setChecked(not(checked, leftChecked));
    };

    const handleCheckedLeft = () => {
        setLeft(left.concat(rightChecked));
        setRight(not(right, rightChecked));
        setChecked(not(checked, rightChecked));
    };

    const handleAllLeft = () => {
        setLeft(left.concat(right));
        setRight([]);
    };

    const handleOpen = () => {
        if(right.length > 0){
            setOpen(true);
        }
        else {
            enqueueSnackbar("Najpierw musisz wybrac pojazdy", {variant: "warning"})
        }
    };
    const handleClose = () => {
        setOpen(false);
    };

    const handleDiscountChange = (ev: React.ChangeEvent<HTMLInputElement>) => {
        const value = Math.max(1, Math.min(100, +ev.target.value));
        setDiscount(value);
    }

    const handleSubmitWithoutDiscount = () => {

        const postData = async () => {
            const offer: Offer = new Offer(
                (params.id ?? -1) as number,
                true,
                0,
                right,
                ""
            )

            await axios.post("http://localhost:8080/api/offers", offer)
        }

        postData()
            .finally(() => {
                handleClose()
                navigate('/companies', {replace: true})
                enqueueSnackbar('Pomyslnie wyslano oferte bez rabatu', {variant: 'success'})
            }).catch(console.error)
    }

    const handleSubmitWithDiscount = () => {

        const postData = async () => {
            const offer: Offer = new Offer(
                (params.id ?? -1) as number,
                true,
                discount,
                right,
                ""
            )

            await axios.post("http://localhost:8080/api/offers", offer)
        }

        postData()
            .finally(() => {
                handleClose()
                navigate('/companies', {replace: true})
                enqueueSnackbar(`Pomyslnie dodano oferte z rabatem ${discount}%`, {variant: 'success'})
            })
            .catch(console.error)
    }

    return (
        <Stack height={'100%'}>
            <Paper variant={"outlined"} sx={{
                minWidth: '1200px',
                padding: '0 20px 20px 20px',
                margin: 'auto',
                display: 'flex',
                flexDirection: 'column',
                height: 'calc(100vh - 2em)'
            }}>
                <Stack flexDirection={"row"} justifyContent={'space-between'}>
                    <IconButton sx={{alignSelf: 'flex-start', transform: 'translateX(-20px)'}} onClick={() => {
                        navigate(`/companies/${params.id}`, {replace: true})
                    }}><ArrowBack/></IconButton>
                    <Button onClick={handleOpen} sx={{transform: 'translateX(20px)'}}>Zaproponuj</Button>
                </Stack>
                <Divider orientation={"horizontal"}
                         sx={{transform: "translateX(-20px)", marginBottom: '1em', width: 'calc(100% + 40px)'}}/>

                <Stack height={'100%'} flexDirection={'row'} gap={'2em'}>
                    <Paper variant={"outlined"} sx={{flex: '1 0 auto', width: 0, overflow: 'auto'}}>
                        <List sx={{height: 0, width: '100%'}}>
                            {left && left.map((car) => {
                                return (
                                    <Tooltip key={car.vin} title={`Liczba napraw wynosi: ${car.repairAmount}`}
                                             enterDelay={500}>
                                        <ListItemButton onClick={handleToggle(car)} key={car.vin}>
                                            <ListItemIcon><Checkbox checked={checked.indexOf(car) !== -1} tabIndex={-1}
                                                                    disableRipple/></ListItemIcon>
                                            <ListItemText>{car.name}</ListItemText>
                                        </ListItemButton>
                                    </Tooltip>
                                )
                            })}
                        </List>
                    </Paper>

                    <Stack alignSelf={'center'}>
                        <Button
                            sx={{my: 0.5}}
                            variant="outlined"
                            size="small"
                            aria-label="move all right"
                            onClick={handleAllRight}
                            disabled={left.length === 0}
                        >
                            ≫
                        </Button>
                        <Button
                            sx={{my: 0.5}}
                            variant="outlined"
                            size="small"
                            aria-label="move selected right"
                            onClick={handleCheckedRight}
                            disabled={leftChecked.length === 0}
                        >
                            &gt;
                        </Button>
                        <Button
                            sx={{my: 0.5}}
                            variant="outlined"
                            size="small"
                            aria-label="move selected left"
                            onClick={handleCheckedLeft}
                            disabled={rightChecked.length === 0}
                        >
                            &lt;
                        </Button>
                        <Button
                            sx={{my: 0.5}}
                            variant="outlined"
                            size="small"
                            aria-label="move all left"
                            onClick={handleAllLeft}
                            disabled={right.length === 0}
                        >
                            ≪
                        </Button>
                    </Stack>

                    <Paper variant={"outlined"} sx={{flex: '1 0 auto', width: 0, overflow: 'auto'}}>
                        <List sx={{height: 0, width: '100%'}}>
                            {right && right.map((car) => {
                                return (
                                    <Tooltip key={car.vin} title={`Liczba napraw wynosi: ${car.repairAmount}`}
                                             enterDelay={1000}>
                                        <ListItemButton onClick={handleToggle(car)}>
                                            <ListItemIcon><Checkbox checked={checked.indexOf(car) !== -1} tabIndex={-1}
                                                                    disableRipple/></ListItemIcon>
                                            <ListItemText>{car.name}</ListItemText>
                                        </ListItemButton>
                                    </Tooltip>
                                )
                            })}
                        </List>
                    </Paper>
                </Stack>
            </Paper>

            <div>
                <Modal
                    open={open}
                    onClose={handleClose}
                    aria-labelledby="parent-modal-title"
                    aria-describedby="parent-modal-description"
                >
                    <Box sx={{
                        position: 'absolute',
                        top: '50%',
                        left: '50%',
                        transform: 'translate(-50%, -50%)',
                        width: 400,
                        bgcolor: 'background.paper',
                        border: '2px solid theme.primary',
                        boxShadow: 24,
                        padding: '2em',
                        borderRadius: '5px',
                        display: 'flex',
                        justifyContent: 'center',
                        flexDirection: 'column',
                        gap: '1em'
                    }}>
                        <Paper variant={'outlined'} sx={{display: 'flex', flexDirection: 'row'}}>
                            <Checkbox checked={discountSwitch} onChange={() => setDiscountSwitch(prev => !prev)}
                                      sx={{'& .MuiSvgIcon-root': {fontSize: 26}}} icon={<DiscountOutlined/>}
                                      checkedIcon={<Discount/>}/>
                            <Divider orientation={'vertical'} flexItem/>
                            <InputBase value={discount} onChange={handleDiscountChange} type={'number'}
                                       sx={{padding: '0 0.5em'}} disabled={!discountSwitch} fullWidth/>
                        </Paper>
                        {
                            discountSwitch ?
                                (
                                    <Button onClick={handleSubmitWithDiscount}>Zapropobuj z rabatem</Button>
                                ) :
                                (
                                    <Button onClick={handleSubmitWithoutDiscount}>Zaproponuj bez rabatu</Button>
                                )
                        }
                    </Box>
                </Modal>
            </div>
        </Stack>

    );
}

