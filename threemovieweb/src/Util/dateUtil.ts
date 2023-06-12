export const week = ['일', '월', '화', '수', '목', '금', '토'];

export const chgDateListForm = (date: number) => {
	const dateStr = date.toString()
	return `${dateStr.slice(0, 4)}\n${dateStr.slice(4, 6)}/${dateStr.slice(6, 8)} (${retWeek(date)})`
}

export const chgTimeForm = (time: number) => {
	const timeStr = time.toString()
	return `${timeStr.slice(8, 10)}:${timeStr.slice(10, 12)}`
}

export const chgDateForm = (date: number) => {
	let dateStr = date.toString()
	if (dateStr.length < 9)
		dateStr = `${dateStr.slice(0, 4)}-${dateStr.slice(4, 6)}-${dateStr.slice(6, 8)}`
	else
		dateStr = `${dateStr.slice(0, 4)}-${dateStr.slice(4, 6)}-${dateStr.slice(6, 8)} ${dateStr.slice(8, 10)}:${dateStr.slice(10, 12)}`
	
	return dateStr
}

export const retWeek = (date: number) => {
	const dateStr = chgDateForm(date);
	const when = new Date(dateStr);
	return week[when.getDay()];
}

export const retWeekClass = (date: number) => {
	let className = '';
	const dateWeek = retWeek(date);
	if (dateWeek === '토') className += ' sat';
	else if (dateWeek === '일') className += ' sun';
	return className;
};
