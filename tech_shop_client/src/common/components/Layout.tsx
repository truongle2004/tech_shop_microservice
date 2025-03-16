import Nav from 'react-bootstrap/Nav'
import Container from 'react-bootstrap/Container'
import Navbar from 'react-bootstrap/Navbar'
import NavDropdown from 'react-bootstrap/NavDropdown'
import { Outlet } from 'react-router-dom'
import type { FC } from 'react'
import { FaShoppingCart } from 'react-icons/fa'

const Layout: FC = () => {
  return (
    <>
      <Navbar bg="dark" variant="dark" expand="lg">
        <Container>
          <Navbar.Brand href="/tech_shop/pages/home">GEARN</Navbar.Brand>
          <Navbar.Toggle aria-controls="basic-navbar-nav" />
          <Navbar.Collapse id="basic-navbar-nav">
            <Nav className="ms-auto">
              <Nav.Link
                href="/tech_shop/pages/carts"
                className="text-danger fs-5"
              >
                <FaShoppingCart /> cart
              </Nav.Link>
              <NavDropdown
                title={
                  <img
                    src="https://via.placeholder.com/40"
                    alt="User"
                    className="rounded-circle"
                    width="40"
                    height="40"
                  />
                }
                id="user-dropdown"
                align="end"
              >
                <NavDropdown.Item href="#profile">Profile</NavDropdown.Item>
                <NavDropdown.Item href="#settings">Settings</NavDropdown.Item>
                <NavDropdown.Divider />
                <NavDropdown.Item href="#logout">Logout</NavDropdown.Item>
              </NavDropdown>
            </Nav>
          </Navbar.Collapse>
        </Container>
      </Navbar>
      <Outlet />
    </>
  )
}

export default Layout
