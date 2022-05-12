import React from "react";
import BuildingService from "../services/BuildingService";
import {render} from "react-dom";

class BuildingComponent extends React.Component{

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

    render(){
        return(
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
        )
    }

}

export default BuildingComponent