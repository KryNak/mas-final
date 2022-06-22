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

function intersect(whole: Car[], subset: Car[]): Car[] {
    return whole.filter((item) => subset.indexOf(item) !== -1)
}

function not(whole: Car[], subset: Car[]): Car[] {
    return whole.filter((item) => subset.indexOf(item) === -1)
}

function CarsPage() {

    const navigate: NavigateFunction = useNavigate()
    const params: Params = useParams()

    const [cars, setCars] = useState<Car[]>([])
    const [unselectedCars, setUnselectedCars] = useState<Car[]>([])
    const [selectedCars, setSelectedCars] = useState<Car[]>([])

    const [leftChecked, setLeftChecked] = useState<Car[]>([])
    const [rightChecked, setRightChecked] = useState<Car[]>([])

    useEffect(() => {
        const fetchData = async () => {
            const response = await axios.get<Car[]>("http://localhost:8080/api/cars")
            const data = response.data

            setCars(data)
            setUnselectedCars(data)
        }

        fetchData().catch(console.error)
    }, [])

    function handleTransferToRight() {

    }

    function handleTransferLeft() {

    }

    function handleCheck(car: Car) {

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
                    <IconButton sx={{alignSelf: 'flex-start', transform: 'translateX(-20px)'}} onClick={() => {navigate(`/companies/${params.id}`, {replace: true})}}><ArrowBack/></IconButton>
                    <Button color={"success"} sx={{transform: 'translateX(20px)'}}>Zaproponuj</Button>
                </Stack>
                <Divider orientation={"horizontal"} sx={{transform: "translateX(-20px)", marginBottom: '1em', width: 'calc(100% + 40px)'}}/>

                <Stack height={'100%'} flexDirection={'row'} gap={'2em'}>
                    <Box sx={{flex: '1 0 auto', width: 0, display: 'flex', flexDirection: 'column'}}>
                        <Paper sx={{
                            padding: "2px 4px",
                            display: 'flex',
                            alignItems: 'center',
                            width: '100%',
                            marginBottom: '20px'
                        }}>
                            <InputBase
                                sx={{ml: 1, flex: 1}}
                                inputProps={{'aria-label': 'search'}}
                            />
                        </Paper>
                        <Paper variant={"outlined"} sx={{flex: '1 1 auto', overflow: 'auto'}}>
                            <List sx={{height: 0, width: '100%'}}>
                                {unselectedCars && unselectedCars.map((car) => {
                                    return (
                                        <ListItemButton onClick={() => handleCheck(car)} key={car.vin}>
                                            <ListItemIcon><Checkbox/></ListItemIcon>
                                            <ListItemText>{car.name}</ListItemText>
                                        </ListItemButton>
                                    )
                                })}
                            </List>
                        </Paper>
                    </Box>

                    <Stack alignSelf={'center'}>
                        <Button
                            sx={{ my: 0.5 }}
                            variant="outlined"
                            size="small"
                            aria-label="move all right"
                        >
                            ≫
                        </Button>
                        <Button
                            sx={{ my: 0.5 }}
                            variant="outlined"
                            size="small"
                            aria-label="move selected right"
                            onClick={handleTransferToRight}
                        >
                            &gt;
                        </Button>
                        <Button
                            sx={{ my: 0.5 }}
                            variant="outlined"
                            size="small"
                            aria-label="move selected left"
                            onClick={handleTransferLeft}
                        >
                            &lt;
                        </Button>
                        <Button
                            sx={{ my: 0.5 }}
                            variant="outlined"
                            size="small"
                            aria-label="move all left"
                        >
                            ≪
                        </Button>
                    </Stack>

                    <Box sx={{flex: '1 0 auto', display: 'flex', flexDirection: 'column', width: '0'}}>
                        <Paper sx={{
                            padding: "2px 4px",
                            display: 'flex',
                            alignItems: 'center',
                            width: '100%',
                            marginBottom: '20px'
                        }}>
                            <InputBase
                                sx={{ml: 1, flex: 1}}
                                inputProps={{'aria-label': 'search'}}
                            />
                        </Paper>
                        <Paper variant={"outlined"} sx={{flex: '1 1 auto', overflow: 'auto'}}>
                            <List sx={{height: 0, width: '100%'}}>
                                {selectedCars && selectedCars.map((car) => {
                                    return (
                                        <ListItemButton key={car.vin}>
                                            <ListItemIcon><Checkbox/></ListItemIcon>
                                            <ListItemText>{car.name}</ListItemText>
                                        </ListItemButton>
                                    )
                                })}
                            </List>
                        </Paper>
                    </Box>
                </Stack>
            </Paper>

        </Stack>
    );
}

