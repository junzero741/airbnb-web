import styled from "styled-components";

export default function Tour({}: {}) {
	return (
		<>
			<StyleList>
				<li>숙소</li>
				<li>체험</li>
				<li>온라인 체험</li>
			</StyleList>
		</>
	);
}

export const StyleList = styled.ul`
	display: flex;
	justify-content: center;
	li {
		cursor: pointer;
		padding: 0 20px;
	}
`;
