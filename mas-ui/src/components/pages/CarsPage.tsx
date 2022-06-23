import {
    Box, Button,
    Checkbox,
    Divider,
    IconButton,
    InputBase,
    List, ListItem, ListItemButton, ListItemIcon, ListItemText, Paper,
    Stack
} from '@mui/material';
import React, {useEffect, useState} from 'react';
import {ArrowBack} from "@mui/icons-material";
import {NavigateFunction, Params, useNavigate, useParams} from "react-router-dom";
import {Car} from "../../models/Car";
import axios from "axios";

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
                    <IconButton sx={{alignSelf: 'flex-start', transform: 'translateX(-20px)'}} onClick={() => {navigate(`/companies/${params.id}`, {replace: true})}}><ArrowBack/></IconButton>
                    <Button color={"success"} sx={{transform: 'translateX(20px)'}}>Zaproponuj</Button>
                </Stack>
                <Divider orientation={"horizontal"} sx={{transform: "translateX(-20px)", marginBottom: '1em', width: 'calc(100% + 40px)'}}/>

                <Stack height={'100%'} flexDirection={'row'} gap={'2em'}>
                    <Paper variant={"outlined"} sx={{flex: '1 0 auto', width: 0, overflow: 'auto'}}>
                        <List sx={{height: 0, width: '100%'}}>
                            {left && left.map((car) => {
                                return (
                                    <ListItemButton onClick={handleToggle(car)} key={car.vin}>
                                        <ListItemIcon><Checkbox checked={checked.indexOf(car) !== -1} tabIndex={-1} disableRipple/></ListItemIcon>
                                        <ListItemText>{car.name}</ListItemText>
                                    </ListItemButton>
                                )
                            })}
                        </List>
                    </Paper>

                    <Stack alignSelf={'center'}>
                        <Button
                            sx={{ my: 0.5 }}
                            variant="outlined"
                            size="small"
                            aria-label="move all right"
                            onClick={handleAllRight}
                            disabled={left.length === 0}
                        >
                            ≫
                        </Button>
                        <Button
                            sx={{ my: 0.5 }}
                            variant="outlined"
                            size="small"
                            aria-label="move selected right"
                            onClick={handleCheckedRight}
                            disabled={leftChecked.length === 0}
                        >
                            &gt;
                        </Button>
                        <Button
                            sx={{ my: 0.5 }}
                            variant="outlined"
                            size="small"
                            aria-label="move selected left"
                            onClick={handleCheckedLeft}
                            disabled={rightChecked.length === 0}
                        >
                            &lt;
                        </Button>
                        <Button
                            sx={{ my: 0.5 }}
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
                                    <ListItemButton onClick={handleToggle(car)} key={car.vin}>
                                        <ListItemIcon><Checkbox checked={checked.indexOf(car) !== -1} tabIndex={-1} disableRipple /></ListItemIcon>
                                        <ListItemText>{car.name}</ListItemText>
                                    </ListItemButton>
                                )
                            })}
                        </List>
                    </Paper>
                </Stack>
            </Paper>

        </Stack>
    );
}

