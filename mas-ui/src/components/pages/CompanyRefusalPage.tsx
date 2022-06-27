import {Params, useNavigate, useParams} from "react-router-dom";
import {
    Button,
    IconButton,
    Paper,
    Stack, TextField
} from "@mui/material";
import {ArrowBack} from "@mui/icons-material";
import {useSnackbar} from "notistack";
import {useEffect, useRef, useState} from "react";
import {Company} from "../../models/Company";
import axios from "axios";
import {Offer} from "../../models/Offer";

export { CompanyRefusalPage }

function CompanyRefusalPage(): JSX.Element {

    const navigate = useNavigate()
    const params: Params = useParams()
    const {enqueueSnackbar} = useSnackbar()

    const [company, setCompany] = useState<Company>()
    const refusalRef = useRef<HTMLInputElement>()

    const handleSubmit = () => {
        const postData = async () => {

            const offer: Offer = new Offer(
                (params.id ?? -1) as number,
                false,
                0,
                [],
                    refusalRef.current?.value ?? null
            )
            await axios.post("http://localhost:8080/api/offers", offer)
        }

        postData()
            .finally(() => {
                navigate('/companies', {replace: true})
                enqueueSnackbar(`Odrzucono oferte firmy ${company?.name} `, {variant: 'info'})
            })
            .catch(console.error)
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
                    <TextField inputRef={refusalRef} multiline rows={10} fullWidth/>
                </Stack>
                <Stack sx={{marginTop: '20px'}} direction={'row'} justifyContent={'center'}>
                    <Button onClick={handleSubmit} variant="contained">Wyslij powod odmowy</Button>
                </Stack>
            </Paper>
        </Stack>
    )
}
