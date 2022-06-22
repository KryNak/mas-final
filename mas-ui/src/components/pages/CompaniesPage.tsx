import {Divider, IconButton, InputBase, List, ListItemButton, ListItemText, Paper, Stack} from '@mui/material';
import React, {useEffect, useRef, useState} from 'react';
import {SearchOutlined} from "@mui/icons-material";
import {NavigateFunction, useNavigate} from "react-router-dom";
import {Company} from "../../models/Company";
import axios from "axios";

function CompaniesPage() {

    const navigate: NavigateFunction = useNavigate()

    const [companies, setCompanies] = useState<Company[]>([])
    const [companiesHolder, setCompaniesHolder] = useState<Company[]>([])

    const ref = useRef<HTMLInputElement>()

    useEffect(() => {
        const fetchData = async () => {
            const response = await axios.get<Company[]>("http://localhost:8080/api/companies")
            setCompanies(response.data)
            setCompaniesHolder(response.data)
        }

        fetchData().catch(console.error)
    }, [])

    const handleSearchClick = () => {
        setCompaniesHolder(
            companies.filter((company) => company.name.toLowerCase().startsWith(ref.current?.value.toLowerCase() ?? ''))
        )
    }

    return (
        <Stack height={'100%'}>
            <Paper variant={"outlined"} sx={{
                minWidth: '800px',
                minHeight: '200px',
                padding: '20px',
                margin: 'auto',
                display: 'flex',
                flexDirection: 'column',
                gap: '20px',
                flex: '1 0 auto',
                maxHeight: 'calc(100vh - 2em)'
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
                        inputProps={{'aria-label': 'search google maps', 'ref': ref}}
                        onKeyDown={(e: React.KeyboardEvent<HTMLInputElement>) => {
                            if(e.key === 'Enter') {
                                handleSearchClick()
                            }
                        }}
                    />
                    <Divider sx={{height: '28px', margin: '0.5em'}} orientation="vertical"/>
                    <IconButton onClick={handleSearchClick} sx={{p: '10px'}} aria-label="directions">
                        <SearchOutlined/>
                    </IconButton>
                </Paper>
                <Paper variant={"outlined"} sx={{flex: '1 1 auto', overflow: 'auto'}} >
                    <List sx={{height: '100%', width: '100%',overflowY: 'scroll'}}>
                        {companiesHolder !== null && companiesHolder?.map((company) => (
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
