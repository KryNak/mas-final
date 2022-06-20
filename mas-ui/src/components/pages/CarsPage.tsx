import {
    Checkbox,
    Divider,
    IconButton,
    InputBase,
    List, Paper,
    Stack
} from '@mui/material';
import React from 'react';
import {ArrowBack, SearchOutlined} from "@mui/icons-material";
import {NavigateFunction, Params, useNavigate, useParams} from "react-router-dom";
import {Company} from "../../mock";

export {CarsPage}

function CarsPage() {

    const navigate: NavigateFunction = useNavigate()

    const params: Params = useParams()
    const company = (JSON.parse(localStorage.getItem("companies") ?? "{[]}") as Company[])
        .filter((company) => company.id === params.id)[0]

    return (
        <Stack height={'100%'}>
            <Paper variant={"outlined"} sx={{minWidth: '800px', padding: '20px', margin: 'auto'}}>
                <IconButton onClick={() => {navigate(`/companies/${params.id}`, {replace: true})}}><ArrowBack/></IconButton>
                <Paper sx={{
                    padding: "2px 4px",
                    display: 'flex',
                    alignItems: 'center',
                    width: '100%',
                    marginBottom: '30px',
                    maxWidth: '800px'
                }}>
                    <InputBase
                        sx={{ml: 1, flex: 1}}
                        placeholder="Search Company"
                        inputProps={{'aria-label': 'search google maps'}}
                    />
                    <Divider sx={{height: '28px', margin: '0.5em'}} orientation="vertical"/>
                    <IconButton sx={{p: '10px'}} aria-label="directions">
                        <SearchOutlined/>
                    </IconButton>
                </Paper>
                <Paper variant={"outlined"} sx={{width: '100%', maxWidth: '800px', height: '100%', maxHeight: '1000px'}}>
                    <List sx={{height: '100%', maxHeight: 'inherit', width: '100%',overflowY: 'scroll'}}>

                    </List>
                </Paper>
            </Paper>

        </Stack>
    );
}

