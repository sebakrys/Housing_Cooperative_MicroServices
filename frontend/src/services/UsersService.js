import axios from "axios";
import Zawartosc from "../components/Zawartosc";

const USERS_REST_URL = 'http://localhost:8000/residents-flat-service/';

class UsersService {

    getUsers(){
        const headers = {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Access-Control-Allow-Origin': '*',
            'Authorization': 'Bearer '+Zawartosc.sToken
        };
        return axios.get(USERS_REST_URL+"getPersons", { headers: headers });
    }
}

export default new UsersService();