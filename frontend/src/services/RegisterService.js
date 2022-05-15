import axios from "axios";
import data from "bootstrap/js/src/dom/data";

const BUILDING_REST_URL = 'http://localhost:8000/building-flat-service/';

class RegisterService {

    async registerUser(userJSON) {
        // Register user login info

        //resonse_object.header("Access-Control-Allow-Origin", "*");
        //resonse_object.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");

        // POST request using axios with set headers
        const headers = {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Access-Control-Allow-Origin': '*'
        };
        var postResponse = await axios.post('http://localhost:8000/residents-flat-service/addPerson', userJSON, {headers})
            .then((response) => {
            console.log("RESP:"+response);
            //return postResponse.data;

                return(['ok', data]);
        }, (error) => {
            console.log("EEEE:"+error);
            return(['error', 'Nie zarejestrowano']);
        });



        //const response = await fetch('http://localhost:8084/residents-flat-service/addPerson', requestOptions);


        //return axios.post('http://localhost:8000/residents-flat-service/addPerson', userJSON);

    }
}

export default new RegisterService();