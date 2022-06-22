import {Params, useNavigate, useParams} from "react-router-dom";
import {Button, Card, CardMedia, IconButton, Paper, Stack, TextField} from "@mui/material";
import {Company} from "../../models/Company";
import {ArrowBack} from "@mui/icons-material";
import {useEffect, useState} from "react";
import axios from "axios";

export {CompanyDetailsPage}

function CompanyDetailsPage(): JSX.Element {

    const navigate = useNavigate()
    const params: Params = useParams()

    const [company, setCompany] = useState<Company | null>(null)

    useEffect(() => {
        const fetchData = async () => {
            const response = await axios.get<Company>(`http://localhost:8080/api/companies/${params.id}`)
            setCompany(response.data)
        }

        fetchData().catch(console.error)
    }, [])

    return (
        <Stack height='100%'>

            {company &&
                <Paper variant={"outlined"} sx={{minWidth: '800px', height: 'fit-content', padding: '20px', margin: 'auto'}}>
                    <IconButton onClick={() => {navigate("/companies", {replace: true})}} sx={{alignSelf: 'flex-start', marginBottom: '20px'}}><ArrowBack/></IconButton>
                    <Stack gap={'10px'} direction='row'>
                        <Card sx={{width: '300px', height: '300px', flexShrink: 0}}>
                            <CardMedia image={company.image} height={'300px'} width={'300px'} component={'img'}></CardMedia>
                        </Card>
                        <Stack width='100%' direction='column' gap={'10px'}>
                            <TextField defaultValue={company.name} inputProps={{readOnly: true}} label={"Nazwa"}/>
                            <TextField defaultValue={company.phoneNumber} inputProps={{readOnly: true}} label={"Dane kontaktowe"}/>
                            <TextField defaultValue={company.description} rows={6} sx={{flexGrow: 1}} multiline inputProps={{readOnly: true}} label={"Opis"}/>
                        </Stack>
                    </Stack>
                    <Stack sx={{marginTop: '20px'}} direction={'row'} justifyContent={'space-between'}>
                        <Button onClick={() => navigate(`/companies/${params.id}/commerce`)} variant="contained">Sprawdz zaleznosc</Button>
                        <Button onClick={() => navigate(`/companies/${params.id}/cars`)} variant="contained">Zaproponuj samochod</Button>
                    </Stack>
                </Paper>
            }

        </Stack>
    )
}
