import React from "react";
import BuildingService from "../services/BuildingService";
import {render} from "react-dom";
import ReactDOM from "react-dom";
import axios from "axios";

import "./loginStyles.css";
import registerService from "../services/RegisterService";
import UsersService from "../services/UsersService";

class UsersPage extends React.Component{

    constructor(props) {
        super(props)
        this.state = {
            users:[]
        }
    }

    componentDidMount() {

        UsersService.getUsers().then((response) => {
            this.setState({users: response.data})
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
                    <h1 className="text-center">Users List</h1>
                    <table className="table table-striped">
                        <thead>
                        <tr>
                            <td>First Name</td>
                            <td>Last Name</td>
                            <td>Email</td>
                            <td>telephone</td>
                            <td>login</td>
                            <td>enabled</td>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            this.state.users.map(
                                user =>
                                    <tr key={user.id}>
                                        <td>{user.firstName}</td>
                                        <td>{user.lastName}</td>
                                        <td>{user.email}</td>
                                        <td>{user.telephone}</td>
                                        <td>{user.login}</td>
                                        <td>{String(user.enabled)}</td>
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

export default UsersPage