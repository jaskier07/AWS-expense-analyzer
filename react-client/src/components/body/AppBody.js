import './AppBody.css';
import { Component } from "react";
import UploadExpenses from '../actions/upload-expenses/UploadExpenses';
import UploadCsvInsertFile from '../actions/upload-csv-insert-file/UploadCsvInsertFile';
import ExampleChart from '../charts/ExampleChart';

export default class AppBody extends Component {
    render() {
        return <div className='AppBody2'>
            <header className="App-header">
                <h1>Home</h1>
            </header>
            <UploadExpenses></UploadExpenses>
            <UploadCsvInsertFile></UploadCsvInsertFile>
            <ExampleChart></ExampleChart>
        </div>
    }
}