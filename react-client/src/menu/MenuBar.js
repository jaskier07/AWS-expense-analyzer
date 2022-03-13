import { Component } from "react";
import { Navbar, Container, Nav, NavDropdown } from 'react-bootstrap';

export default class MenuBar extends Component {

    render() {
        return <Navbar bg="light" expand="lg">
            <Container>
                <Navbar.Brand href="#home">AWS Expenses Counter</Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="me-auto">
                        <Nav.Link href="#home">Home</Nav.Link>
                        <NavDropdown title="Developer documentation" id="basic-nav-dropdown">
                            <NavDropdown.Item href="https://recharts.org/en-US/examples/TinyBarChart">Recharts</NavDropdown.Item>
                            <NavDropdown.Item href="https://react-bootstrap.github.io/getting-started/introduction/">React-Bootstrap</NavDropdown.Item>
                            <NavDropdown.Divider />
                            <NavDropdown.Item href="https://reactjs.org/tutorial/tutorial.html#what-is-react">React Documentation</NavDropdown.Item>
                        </NavDropdown>
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    }
}