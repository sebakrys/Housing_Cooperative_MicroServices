import React from "react";
import BuildingService from "../services/BuildingService";
import {render} from "react-dom";
import ReactDOM from "react-dom";
import axios from "axios";

import "./loginStyles.css";
import registerService from "../services/RegisterService";

class BuildingsPage extends React.Component{

    constructor(props) {
        super(props)
        this.state = {
            buildings:[]
        }
    }

    componentDidMount() {
        BuildingService.getBuildings().then((response) => {
            this.setState({buildings: response.data})
        });
    }


    handleSubmit = (event) => {
        //Prevent page reload
        event.preventDefault();
    };


    render(){
        return(
            <div>
            <div>
                <h1 className="text-center">Buildings List</h1>
                <table className="table table-striped">
                    <thead>
                    <tr>
                        <td>Name</td>
                        <td>City</td>
                        <td>Street</td>
                        <td>Number</td>
                        <td>Postal Code</td>
                    </tr>
                    </thead>
                    <tbody>
                    {
                        this.state.buildings.map(
                            building =>
                                <tr key={building.id}>
                                    <td>{building.nazwa}</td>
                                    <td>{building.city}</td>
                                    <td>{building.street}</td>
                                    <td>{building.buildingNumber}</td>
                                    <td>{building.postalCode}</td>
                                </tr>
                        )
                    }
                    </tbody>
                </table>
            </div>
            </div>
        )
    }

}

export default BuildingsPage