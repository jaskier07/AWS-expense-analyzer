import './App.css';
import MenuBar from './components/menu/MenuBar';
import 'bootstrap/dist/css/bootstrap.min.css';
import AppBody from './components/body/AppBody';

function App() {
  return (
    <div className="App">
      <MenuBar></MenuBar>
      <div className="App-body-container">
        <AppBody></AppBody>
      </div>
    </div>
  );
}

export default App;
