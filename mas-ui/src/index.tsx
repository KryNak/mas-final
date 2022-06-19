import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import {StyledEngineProvider} from "@mui/material";
import {Route, BrowserRouter as Router, Routes, Navigate} from 'react-router-dom'
import CompaniesPage from "./components/pages/CompaniesPage";
import {CompanyPage} from "./components/pages/CompanyPage";
import {Company, Database} from "./mock";
import {CompanyCommercePage} from "./components/pages/CompanyCommercePage";
import {CompanyRefusalPage} from "./components/pages/CompanyRefusalPage";
import {CarsPage} from "./components/pages/CarsPage";

if(localStorage.getItem("companies") == null){
    Database.setup()
    const companies: Company[] = Database.companies
    localStorage.setItem("companies", JSON.stringify(companies))
}

const root = ReactDOM.createRoot(
    document.getElementById('root') as HTMLElement
);
root.render(
    <StyledEngineProvider injectFirst>
        <Router>
            <Routes>
                <Route path="*" element={<Navigate to={"/companies"} replace />}></Route>
                <Route path={"/companies"} element={<CompaniesPage/>}></Route>
                <Route path={"/companies/:id"} element={<CompanyPage/>} />
                <Route path={"/companies/:id/commerce"} element={<CompanyCommercePage/>} />
                <Route path={"/companies/:id/refusal"} element={<CompanyRefusalPage/>} />
                <Route path={"/companies/:id/cars"} element={ <CarsPage/> } />
            </Routes>
        </Router>
    </StyledEngineProvider>
);
