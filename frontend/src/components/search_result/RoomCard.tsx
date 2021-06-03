import { useState, useEffect } from "react";
import styled from "styled-components";
import makeKRW from "./../../utils/makeKRW";
import { ReactComponent as Heart } from "./../../icons/heart.svg";
import BookModal from "./BookModal";

export interface IAppProps {
	info: any;
}

export default function RoomCard({ info }: IAppProps) {
	const [isModalOn, setIsModalOn] = useState(false);
	const image = info.thumbImage;
	const { maxGuest, bathroom, bed, title, description, pricePerDay } = info.room;
	const { basicPrice, cleaningFee, resultFee, serviceFee, totalPrice, weekSalePrice } = info.receipt;

	const handleOnClick = () => {
		setIsModalOn(true);
	};

	return (
		<>
			{isModalOn ? <BookModal info={info} setIsModalOn={setIsModalOn} /> : <></>}
			<>
				<StyleCard onClick={handleOnClick}>
					<Wrapper>
						<img className="thumb" src={image} alt={image} width="330" height="200" />
						<Info>
							<div>
								<div className="description">{description}</div>
								<div className="heart">
									<Heart />
								</div>
								<div className="title">{title}</div>
								<div className="detail">
									최대인원 ${maxGuest}명 - 침대 {bed}개 - 욕실{bathroom}개
								</div>
							</div>
							<PricePerDay>
								<span className="price">￦{makeKRW(pricePerDay)}</span> / 박
							</PricePerDay>
							<Bottom>
								<div>후기 N 개</div>
								<div>총액 ￦{makeKRW(totalPrice)}</div>
							</Bottom>
						</Info>
					</Wrapper>
				</StyleCard>
			</>
		</>
	);
}

const StyleCard = styled.div`
	width: 100%;
	height: 212px;
	margin-bottom: 20px;
	border-bottom: 1px solid #e0e0e0;
	:hover {
		cursor: pointer;
		width: 105%;
		height: 215px;
		box-shadow: 0px -4px 4px rgba(204, 204, 204, 0.5), 0px 4px 4px rgba(0, 0, 0, 0.25);
	}
`;

const Wrapper = styled.div`
	display: flex;
	height: 200px;
	.thumb {
		border-radius: 10px;
	}
`;

const Info = styled.div`
	display: flex;
	flex-direction: column;
	justify-content: space-between;
	width: 518.02px;
	height: 100%;
	padding-left: 10px;

	.heart {
		position: relative;
		float: right;
		top: -25px;
	}

	.description,
	.detail {
		font-size: 12px;
		color: #828282;
		padding: 10px 0;
	}

	.title {
		font-size: 14px;
		color: #333333;
		padding: 10px 0;
	}
`;

const PricePerDay = styled.div`
	display: flex;
	justify-content: flex-end;
	align-items: flex-end;
	height: 275px;
	font-size: 14px;
	color: #333333;
	padding: 10px 0;
	.price {
		font-weight: 700;
		margin-right: 2px;
	}
`;

const Bottom = styled.div`
	display: flex;
	width: 100%;
	justify-content: space-between;
`;
