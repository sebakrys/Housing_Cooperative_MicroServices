import React, {useEffect} from "react";
import FlatService from "../services/FlatService";
import {render} from "react-dom";
import ReactDOM from "react-dom";
import axios from "axios";
import {useParams, useSearchParams} from "react-router-dom";

import "./loginStyles.css";
import registerService from "../services/RegisterService";
import {useLocation} from "react-router";
import BuildingService from "../services/BuildingService";
import Zawartosc from "../components/Zawartosc";

class FlatChargesPage extends React.Component{



constructor(props) {

        super(props)
  /*      this.state = {
            flats:[]
        }*/
    this.state = {
        flatCharges:[],
        flat:[]
    };

    }

    componentDidMount() {
        console.log(window.location.href.substring(window.location.href.indexOf("flatCharges/")+12));
        console.log(JSON.stringify(window.location.href));
        const id = window.location.href.substring(window.location.href.indexOf("flatCharges/")+12)
        FlatService.getFlatCharges(id).then((response) => {
            this.setState({flatCharges: response.data})
        });

        BuildingService.getBuilding(id).then((response) => {
            this.setState({flat: response.data})
        });


        console.log("aaaaaa  "+JSON.stringify(this.flat))
        console.log("aaaaaa  "+JSON.stringify(this.state.flat))
    }


    handleSubmit = (event) => {
        //Prevent page reload
        event.preventDefault();
    };


    render(){
        return(
            <div>
            <div>
                <h1 className="text-center">Flat Charges List {console.log(JSON.stringify(this.state.flatCharges))}</h1>

                <table className="table table-striped">
                    <thead>
                    <tr>
                        <td>Data</td>
                        <td>Accepted</td>
                        <td>Zaplacone</td>
                        <td>Prad</td>
                        <td>Gaz</td>
                        <td>Woda ciepla</td>
                        <td>Woda zimna</td>
                        <td>Scieki</td>
                        <td>Ogrzewanie</td>
                        <td>Fundusz Remontowy</td>
                    </tr>
                    </thead>
                    <tbody>
                    {
                        this.state.flatCharges.map(
                            flatCharges =>
                                <tr key={flatCharges.id}>
                                    <td>{flatCharges.data}</td>
                                    <td>{String(flatCharges.accepted)}</td>
                                    <td>{String(flatCharges.zaplacone)}</td>
                                    <td>{flatCharges.prad}</td>
                                    <td>{flatCharges.gaz}</td>
                                    <td>{flatCharges.woda_ciepla}</td>
                                    <td>{flatCharges.woda_zimna}</td>
                                    <td>{flatCharges.scieki}</td>
                                    <td>{flatCharges.ogrzewanie}</td>
                                    <td>{flatCharges.funduszRemontowy}</td>
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

export default FlatChargesPage

