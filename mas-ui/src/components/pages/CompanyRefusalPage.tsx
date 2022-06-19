import {Params, useNavigate, useParams} from "react-router-dom";
import {Company} from "../../mock";
import {
    Button,
    IconButton,
    Paper,
    Stack, TextField
} from "@mui/material";
import {ArrowBack} from "@mui/icons-material";

export { CompanyRefusalPage }

function CompanyRefusalPage(): JSX.Element {

    const navigate = useNavigate()

    const params: Params = useParams()
    const company = (JSON.parse(localStorage.getItem("companies") ?? "{[]}") as Company[])
        .filter((company) => company.id === params.id)[0]

    return (
        <Stack height='100%'>
            <Paper variant={"outlined"} sx={{minWidth: '800px', height: 'fit-content', padding: '20px', margin: 'auto'}}>
                <IconButton onClick={() => {navigate(`/companies/${company.id}`, {replace: true})}} sx={{alignSelf: 'flex-start', marginBottom: '20px'}}><ArrowBack/></IconButton>
                <Stack gap={'10px'} direction='column' alignItems={'center'}>
                    <TextField multiline rows={10} fullWidth/>
                </Stack>
                <Stack sx={{marginTop: '20px'}} direction={'row'} justifyContent={'center'}>
                    <Button variant="contained">Wyslij powod odmowy</Button>
                </Stack>
            </Paper>
        </Stack>
    )
}
