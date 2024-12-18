import React from 'react';
import './style/style.css';
import Banner from "../../components/Banner";
import Poll from "../../components/Poll";

const Logo = require('../../assets/logo.png');

function Index() {
  return (
    <div className="App">
        <header className="App-header">
            <Banner />
        </header>
        <div className="App-content">
            <Poll />
        </div>
    </div>
  );
}

export default Index;
