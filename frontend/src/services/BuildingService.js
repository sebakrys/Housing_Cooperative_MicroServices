import axios from "axios";

const BUILDING_REST_URL = 'http://localhost:8000/building-flat-service/';

class BuildingService {

    getBuildings(){
        return axios.get(BUILDING_REST_URL+"getBuildings");
    }
}

export default new BuildingService();