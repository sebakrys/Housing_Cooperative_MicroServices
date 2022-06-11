import axios from "axios";
import Zawartosc from "../components/Zawartosc";

const BUILDING_REST_URL = 'http://localhost:8000/building-flat-service/';

class BuildingService {

    getBuildings(){
        const headers = {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Access-Control-Allow-Origin': '*',
            'Authorization': 'Bearer '+Zawartosc.sToken
        };
        return axios.get(BUILDING_REST_URL+"getBuildings", { headers: headers });
    }

    getBuilding(bID){
        const headers = {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Access-Control-Allow-Origin': '*',
            'Authorization': 'Bearer '+Zawartosc.sToken
        };
        return axios.get(BUILDING_REST_URL+"getBuilding"+bID, { headers: headers });
    }
}

export default new BuildingService();