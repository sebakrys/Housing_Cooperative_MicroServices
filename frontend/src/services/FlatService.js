import axios from "axios";
import Zawartosc from "../components/Zawartosc";

const BUILDING_REST_URL = 'http://localhost:8000/building-flat-service/';

class FlatService {

    getFlats(buildingID){
        const headers = {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Access-Control-Allow-Origin': '*',
            'Authorization': 'Bearer '+Zawartosc.sToken
        };
        return axios.get(BUILDING_REST_URL+"getBuildingFlats/"+buildingID, { headers: headers });
    }

    getFlatCharges(flatID){
        const headers = {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Access-Control-Allow-Origin': '*',
            'Authorization': 'Bearer '+Zawartosc.sToken
        };
        return axios.get(BUILDING_REST_URL+"getFlatFlatCharges/"+flatID, { headers: headers });
    }
}

export default new FlatService();