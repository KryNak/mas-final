import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import {StyledEngineProvider} from "@mui/material";
import {Route, BrowserRouter as Router, Routes, Navigate} from 'react-router-dom'
import CompaniesPage from "./components/pages/CompaniesPage";
import {CompanyDetailsPage} from "./components/pages/CompanyDetailsPage";
import {CompanyCommercePage} from "./components/pages/CompanyCommercePage";
import {CompanyRefusalPage} from "./components/pages/CompanyRefusalPage";
import {CarsPage} from "./components/pages/CarsPage";

const root = ReactDOM.createRoot(
    document.getElementById('root') as HTMLElement
);

root.render(
    <StyledEngineProvider injectFirst>
        <Router>
            <Routes>
                <Route path="*" element={<Navigate to={"/companies"} replace />}></Route>
                <Route path={"/companies"} element={<CompaniesPage/>}></Route>
                <Route path={"/companies/:id"} element={<CompanyDetailsPage/>} />
                <Route path={"/companies/:id/commerce"} element={<CompanyCommercePage/>} />
                <Route path={"/companies/:id/refusal"} element={<CompanyRefusalPage/>} />
                <Route path={"/companies/:id/cars"} element={ <CarsPage/> } />
            </Routes>
        </Router>
    </StyledEngineProvider>
);
