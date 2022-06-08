import logo from './logo.svg';
import './App.css';
import { BrowserRouter as Router, Routes, Route}
    from 'react-router-dom';
import BuildingComponent from "./components/BuildingComponent";
import LoginPage from "./pages/LoginPage";
import RegisterPage from "./pages/RegisterPage";
import BuildingsPage from "./pages/BuildingsPage";
import Navbar from "./components/Navbar";
import AddBuildingPage from "./pages/AddBuildingPage";
import Welcome from './pages/Welcome';
import Secured from './pages/Secured';
import IndexNavbarSecured from "./components/Navbar/indexNavbarSecured";
import Keycloak from "keycloak-js";
import {useEffect, useState} from "react";
import Zawartosc from "./components/Zawartosc";



function App() {

    return (
        <Router>
            <Zawartosc />
        </Router>
    );
}

export default App;
