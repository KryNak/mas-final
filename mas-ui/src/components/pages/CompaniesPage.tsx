import {Divider, IconButton, InputBase, List, ListItemButton, ListItemText, Paper, Stack} from '@mui/material';
import React, {useEffect, useState} from 'react';
import {SearchOutlined} from "@mui/icons-material";
import {NavigateFunction, useNavigate} from "react-router-dom";
import {Company} from "../../models/Company";
import axios from "axios";

function CompaniesPage() {

    const navigate: NavigateFunction = useNavigate()
    const [companies, setCompanies] = useState<Company[]>([])

    useEffect(() => {
        const fetchData = async () => {
            const response = await axios.get<Company[]>("http://localhost:8080/api/companies")
            setCompanies(response.data)
        }

        fetchData().catch(console.error)
    }, [])

    return (
        <Stack height={'100%'} sx={{padding: '1em 0 1em 0'}}>
            <Paper variant={"outlined"} sx={{
                minWidth: '800px',
                height: 'max-content',
                minHeight: '200px',
                padding: '20px',
                margin: 'auto',
                display: 'flex',
                flexDirection: 'column',
                gap: '20px'
            }}>
                <Paper sx={{
                    padding: "2px 4px",
                    display: 'flex',
                    alignItems: 'center',
                    width: '100%',
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
                <Paper variant={"outlined"} sx={{flex: '1 1 auto', overflow: 'auto'}} >
                    <List sx={{height: '100%', width: '100%',overflowY: 'scroll'}}>
                        {companies !== null && companies?.map((company) => (
                            <ListItemButton key={company.id} onClick={() => {navigate(`/companies/${company.id}`)}}>
                                <ListItemText>
                                    {company.name}
                                </ListItemText>
                            </ListItemButton>
                        ))}
                    </List>
                </Paper>
            </Paper>

        </Stack>
    );
}

export default CompaniesPage;
