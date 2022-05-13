import axios from "axios";

const BUILDING_REST_URL = 'http://localhost:8000/building-flat-service/';

class RegisterService {

    async registerUser(userJSON) {
        // Register user login info

        //resonse_object.header("Access-Control-Allow-Origin", "*");
        //resonse_object.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");

        const requestOptions = {
            mode: 'no-cors',
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: userJSON
        };
        const response = await fetch('http://localhost:8000/residents-flat-service/addPerson', requestOptions);


        //return axios.post('http://localhost:8000/residents-flat-service/addPerson', userJSON);

    }
}

export default new RegisterService();