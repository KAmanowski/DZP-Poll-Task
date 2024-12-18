import React from 'react';
import './style/style.css';

const Logo = require('../../assets/logo.png');

function Banner() {
    return (
        <div>
            <img src={Logo} className="logo" alt="logo"/>
        </div>
    );
}

export default Banner;
