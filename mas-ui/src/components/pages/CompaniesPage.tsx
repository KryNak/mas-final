import {
    Divider,
    IconButton,
    InputBase,
    List,
    ListItemButton,
    ListItemText,
    Paper,
    Stack
} from '@mui/material';
import React from 'react';
import {SearchOutlined} from "@mui/icons-material";
import {NavigateFunction, useNavigate} from "react-router-dom";
import {Company} from "../../mock";

function CompaniesPage() {

    const navigate: NavigateFunction = useNavigate()
    let companies: Company[] = JSON.parse(localStorage.getItem("companies") ?? '{[]}') as Company[]

    return (
        <Stack height={'100%'}>
            <Paper variant={"outlined"} sx={{minWidth: '800px', height: 'fit-content', padding: '20px', margin: 'auto'}}>
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
                <Paper variant={"outlined"} sx={{flexShrink: 0, width: '100%', maxWidth: '800px', height: '100%', maxHeight: '1000px'}}>
                    <List sx={{height: '100%', maxHeight: 'inherit', width: '100%',overflowY: 'scroll'}}>
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
