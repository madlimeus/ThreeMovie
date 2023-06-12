const dateFormat = (date: string | number | null) => {
	if (typeof date === "number") {
		const str = date.toString()
		
		return `${str.slice(0, 4)}.${str.slice(4, 6)}.${str.slice(6, 8)}`;
	}
	if (date)
		return `${date.slice(0, 4)}.${date.slice(4, 6)}.${date.slice(6, 8)}`;
	
	return date;
}

export default dateFormat;
