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


function App() {
    return (
        <Router>
            <Navbar />

            <Routes>
                <Route exact path='/' exact element={<LoginPage />} />
                <Route path="/public" element={<Welcome/>} />
                <Route path="/secured" element={<Secured/>} />
                <Route path='/login' element={<LoginPage/>} />
                <Route path='/register' element={<RegisterPage/>} />
                <Route path='/buildings' element={<BuildingsPage/>} />
                <Route path='/addbuilding' element={<AddBuildingPage/>} />
            </Routes>
        </Router>
    );
}

export default App;
