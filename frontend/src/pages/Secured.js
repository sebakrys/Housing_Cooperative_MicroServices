import React, { Component } from 'react';
import Keycloak from 'keycloak-js';

class Secured extends Component {

    constructor(props) {
        super(props);
        this.state = { keycloak: null, authenticated: false };
        this.authInProgSecured = false;
    }




    componentDidMount() {
        if(!this.authInProgSecured){
            this.authInProgSecured = true;
            //alert("componentDidMount")
            const keycloak = Keycloak('/keycloak.json');
            keycloak.init({onLoad: 'login-required'}).then(authenticated => {
                this.setState({ keycloak: keycloak, authenticated: authenticated });
            })
        }

    }

    componentDidUpdate() {
        if(this.authInProgSecured)this.authInProgSecured=false;
    }

    render() {
        if (this.state.keycloak) {
            if (this.state.authenticated){return (
                <div>
                    <p>This is a Keycloak-secured component of your application. You shouldn't be able
                        to see this unless you've authenticated with Keycloak.</p>
                </div>
            )} else return (<div>Unable to authenticate!</div>)
        }
        return (
            <div>Initializing Keycloak...</div>
        );
    }
}
export default Secured;