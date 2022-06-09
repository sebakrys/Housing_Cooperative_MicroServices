import React, { Component } from 'react';

class UserInfo extends Component {

    constructor(props) {
        super(props);
        this.state = {
            name: "",
            email: "",
            username: ""
        };
        this.props.keycloak.loadUserInfo().then(userInfo => {
            this.setState({name: userInfo.name, email: userInfo.email, username: userInfo.preferred_username})
        });
    }

    render() {
        return (
            <div className="UserInfo">
                <p>Name: {this.state.name}</p>
                <p>Email: {this.state.email}</p>
                <p>Login: {this.state.username}</p>
            </div>
        );
    }
}
export default UserInfo;