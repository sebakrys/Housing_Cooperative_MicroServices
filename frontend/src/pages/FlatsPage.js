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

class FlatsPage extends React.Component{



constructor(props) {

        super(props)
  /*      this.state = {
            flats:[]
        }*/
    this.state = {
        flats:[],
        building:[]
    };

    }

    componentDidMount() {
        console.log(window.location.href.substring(window.location.href.indexOf("flats/")+6));
        console.log(JSON.stringify(window.location.href));
        const id = window.location.href.substring(window.location.href.indexOf("flats/")+6)
        FlatService.getFlats(id).then((response) => {
            this.setState({flats: response.data})
        });

        BuildingService.getBuilding(id).then((response) => {
            this.setState({building: response.data})
        });


        console.log("aaaaaa  "+JSON.stringify(this.building))
        console.log("aaaaaa  "+JSON.stringify(this.state.building))
    }


    handleSubmit = (event) => {
        //Prevent page reload
        event.preventDefault();
    };


    render(){
        return(
            <div>
            <div>
                <h1 className="text-center">Flats List </h1>

                <table className="table table-striped">
                    <thead>
                    <tr>
                        <td>Number</td>
                    </tr>
                    </thead>
                    <tbody>
                    {
                        this.state.flats.map(
                            flat =>
                                <tr key={flat.id}>
                                    <td>{flat.flatNumber}</td>
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

export default FlatsPage

