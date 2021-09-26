import styled from "styled-components";

const ModalContainer = styled.div<{ type: string }>`
	width: ${(props) => {
		switch (props.type) {
			case "LOCATION":
			case "GUEST":
				return "400px";
			case "FEE":
				return "493px";
			case "CHECKIN":
				return "1016px";
		}
	}};
	height: ${(props) => {
		switch (props.type) {
			case "GUEST":
				return "355px";
			case "FEE":
				return "364px";
			case "CHECKIN":
				return "512px";
		}
	}};
	left: ${(props) => {
		switch (props.type) {
			case "CHECKIN":
				return "-200px";
			case "GUEST":
				return "-200px";
			case "FEE":
				return "-80px";
		}
	}};
	position: absolute;
	top: 100px;
	display: flex;
	justify-content: center;
	align-items: center;
	border-radius: 40px;
	background-color: #ffffff;
	box-shadow: 0px 4px 10px rgba(51, 51, 51, 0.1), 0px 0px 4px rgba(51, 51, 51, 0.05);
`;

export default ModalContainer;
