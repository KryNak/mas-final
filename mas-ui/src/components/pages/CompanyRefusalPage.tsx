import {Params, useNavigate, useParams} from "react-router-dom";
import {
    Button,
    IconButton,
    Paper,
    Stack, TextField
} from "@mui/material";
import {ArrowBack} from "@mui/icons-material";
import {useSnackbar} from "notistack";
import {useEffect, useState} from "react";
import {Company} from "../../models/Company";
import axios from "axios";

export { CompanyRefusalPage }

function CompanyRefusalPage(): JSX.Element {

    const navigate = useNavigate()
    const params: Params = useParams()
    const {enqueueSnackbar} = useSnackbar()

    const [company, setCompany] = useState<Company>()

    const handleSubmit = () => {
        navigate('/companies', {replace: true})
        enqueueSnackbar(`Odrzucono oferte firmy ${company?.name} `, {variant: 'info'})
    }

    useEffect(() => {
        const fetchData = async () => {
            const response = await axios.get<Company>(`http://localhost:8080/api/companies/${params.id}`)
            setCompany(response.data)
        }

        fetchData().catch(console.error)
    }, [])

    return (
        <Stack height='100%'>
            <Paper variant={"outlined"} sx={{minWidth: '800px', height: 'fit-content', padding: '20px', margin: 'auto'}}>
                <IconButton onClick={() => {navigate(`/companies/${params.id}`, {replace: true})}} sx={{alignSelf: 'flex-start', marginBottom: '20px'}}><ArrowBack/></IconButton>
                <Stack gap={'10px'} direction='column' alignItems={'center'}>
                    <TextField multiline rows={10} fullWidth/>
                </Stack>
                <Stack sx={{marginTop: '20px'}} direction={'row'} justifyContent={'center'}>
                    <Button onClick={handleSubmit} variant="contained">Wyslij powod odmowy</Button>
                </Stack>
            </Paper>
        </Stack>
    )
}
