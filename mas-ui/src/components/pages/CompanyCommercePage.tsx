import {Params, useNavigate, useParams} from "react-router-dom";
import {Company} from "../../mock";
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

export { CompanyCommercePage }

function CompanyCommercePage(): JSX.Element {

    const navigate = useNavigate()

    const params: Params = useParams()
    const company = (JSON.parse(localStorage.getItem("companies") ?? "{[]}") as Company[])
        .filter((company) => company.id === params.id)[0]

    return (
        <Stack height='100%'>
            <Paper variant={"outlined"} sx={{minWidth: '800px', height: 'fit-content', padding: '20px', margin: 'auto'}}>
                <IconButton onClick={() => {navigate(`/companies/${company.id}`, {replace: true})}} sx={{alignSelf: 'flex-start', marginBottom: '20px'}}><ArrowBack/></IconButton>
                <Stack gap={'10px'} direction='column' alignItems={'center'}>
                    <Card sx={{width: '300px', height: '350px', flexShrink: 0}}>
                        {company &&
                            <CardMedia image={company.image} component={'img'}></CardMedia>
                        }
                        <CardContent>
                            <Typography>{company.name}</Typography>
                        </CardContent>
                    </Card>
                    <Typography sx={{marginTop: '20px'}}>Pasek udanych tranzakcji</Typography>
                    <LinearProgress value={20} variant={'determinate'} color={'primary'} sx={{height: '20px', width: '400px'}}/>
                </Stack>
                <Stack sx={{marginTop: '20px'}} direction={'row'} justifyContent={'space-between'}>
                    <Button onClick={() => { navigate(`/companies/${params.id}/refusal`) }} variant="contained">Odrzuc</Button>
                    <Button onClick={() => { navigate(`/companies/${params.id}`, {replace: true}) }} variant="contained">Cofnij</Button>
                </Stack>
            </Paper>
        </Stack>
    )
}
