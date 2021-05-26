import styled from "styled-components";
import Day from "./Day";

interface ConProps {
	data: Date;
}
export default function CalendarCon({ data }: ConProps) {
	const year = data.getFullYear();
	const month = data.getMonth() + 1;
	const lastDay = new Date(year, month, 0).getDate();
	const firstWeekDay = data.getDay();
	const weekList = [];
	const weekTitleList = ["일", "월", "화", "수", "목", "금", "토"];
	const weekDayTitleList = [];
	let day = -firstWeekDay;

	for (let i = 0; i < 7; i++) {
			weekList.push([
				<Week>
				<Day year={year} month={month} day={++day} lastDay={lastDay}key={day}/>
				<Day year={year} month={month} day={++day} lastDay={lastDay}key={day}/>
				<Day year={year} month={month} day={++day} lastDay={lastDay}key={day}/>
				<Day year={year} month={month} day={++day} lastDay={lastDay}key={day}/>
				<Day year={year} month={month} day={++day} lastDay={lastDay}key={day}/>
				<Day year={year} month={month} day={++day} lastDay={lastDay}key={day}/>
				<Day year={year} month={month} day={++day} lastDay={lastDay}key={day}/>
			</Week>,
		]);
		weekDayTitleList.push(<StyleDay key={i}>{weekTitleList[i]}</StyleDay>);
	}
	
// 1~31 들은 배열
// 월의 첫 날짜의 요일

	return (
		<StyleCalendar>
			<HeaderWrapper>
				{year}년 {month}월
			</HeaderWrapper>
			<BodyWrapper>
				<tbody>
					<WeekHeader>{weekDayTitleList}</WeekHeader>
					{weekList}
				</tbody>
			</BodyWrapper>
		</StyleCalendar>
	);
}
const StyleCalendar = styled.div`
	width: 336px;
	height: 336px;
	font-size: 12px;
	margin: 0 30px;
`;

const HeaderWrapper = styled.div`
	display: flex;
	justify-content: center;
	font-size: 16px;
`;
const BodyWrapper = styled.table`
	display: flex;
	flex-direction: column;
	justify-content: space-between;
`;

const WeekHeader = styled.tr`
	display: flex;
	justify-content: space-between;
	td {
		width: 48px;
		height: 48px;
	}
`;

const StyleDay = styled.td`
	display: flex;
	justify-content: center;
	align-items: center;
	td {
		width: 48px;
		height: 48px;
	}
`;

const Week = styled.tr`
	display: flex;
	
`;