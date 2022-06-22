import {Params, useNavigate, useParams} from "react-router-dom";
import {Company} from "../../models/Company";
import {
    Button,
    Card,
    CardContent,
    CardMedia,
    IconButton,
    LinearProgress,
    Paper,
    Stack,
    Typography
} from "@mui/material";
import {ArrowBack} from "@mui/icons-material";
import {useEffect, useState} from "react";
import axios from "axios";

export { CompanyCommercePage }

function CompanyCommercePage(): JSX.Element {

    const navigate = useNavigate()
    const params: Params = useParams()

    const[company, setCompany] = useState<Company | null>(null)

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
                    <IconButton onClick={() => {navigate(`/companies/${company.id}`, {replace: true})}} sx={{alignSelf: 'flex-start', marginBottom: '20px'}}><ArrowBack/></IconButton>
                    <Stack gap={'10px'} direction='column' alignItems={'center'}>
                        <Card sx={{width: '300px', height: '350px', flexShrink: 0}}>
                            <CardMedia image={company.image} height={'300px'} width={'300px'} component={'img'}></CardMedia>
                            <CardContent>
                                <Typography>{company.name}</Typography>
                            </CardContent>
                        </Card>
                        <Typography sx={{marginTop: '20px'}}>Pasek udanych transakcji</Typography>
                        <LinearProgress value={20} variant={'determinate'} color={'primary'} sx={{height: '20px', width: '400px'}}/>
                    </Stack>
                    <Stack sx={{marginTop: '20px'}} direction={'row'} justifyContent={'space-between'}>
                        <Button onClick={() => { navigate(`/companies/${params.id}/refusal`) }} variant="contained">Odrzuc</Button>
                        <Button onClick={() => { navigate(`/companies/${params.id}`, {replace: true}) }} variant="contained">Cofnij</Button>
                    </Stack>
                </Paper>
            }
        </Stack>
    )
}
