'*****************************************
'ʹ��VB�ű���ȡ�õ������ֵ�����ĸ�ļ�ƴ
'*****************************************
Function getSpellOfChar(ch)
	dim asciiS	
	dim bt
	asciiS=65536+asc(ch)  'ȡ��16����ASCII
	if asciiS>=45217 and asciiS<= 45252  then
		bt="A"
	elseif asciiS>=45253 and asciiS<= 45760 then
		bt="B"
	elseif asciiS>=45761 and asciiS<= 46317 then
		bt="C"
	elseif asciiS>=46318 and asciiS<= 46825 then
		bt="D"
	elseif asciiS>=46826 and asciiS<= 47009 then
		bt="E"
	elseif asciiS>=47010 and asciiS<= 47296 then
		bt="F"
	elseif asciiS>=47297 and asciiS<= 47613 then
		bt="G"
	elseif asciiS>=47614 and asciiS<= 48118 then
		bt="H"
	elseif asciiS>=48119 and asciiS<= 49061 then
		bt="J"
	elseif asciiS>=49062 and asciiS<= 49323 then
		bt="K"
	elseif asciiS>=49324 and asciiS<= 49895 then
		bt="L"
	elseif asciiS>=49896 and asciiS<= 50370 then
		bt="M"
	elseif asciiS>=50371 and asciiS<= 50613 then
		bt="N"
	elseif asciiS>=50614 and asciiS<= 50621 then
		bt="O"
	elseif asciiS>=50622 and asciiS<= 50905 then
		bt="P"
	elseif asciiS>=50906 and asciiS<= 51386 then
		bt="Q"
	elseif asciiS>=51387 and asciiS<= 51445 then
		bt="R"
	elseif asciiS>=51446 and asciiS<= 52217 then
		bt="S"
	elseif asciiS>=52218 and asciiS<= 52697 then
		bt="T"
	elseif asciiS>=52698 and asciiS<= 52979 then
		bt="W"
	elseif asciiS>=52980 and asciiS<= 53640 then
		bt="X"
	elseif asciiS>=53641 and asciiS<= 54480 then
		bt="Y"
	elseif asciiS>=54480 and asciiS<= 55289 then
		bt="Z"
	end if
	
	getSpellOfChar=bt
end Function

'**********************************************************
'ȡ���ַ����к���ƴ������ĸ������ǷǺ����ַ����գ�Ӣ�Ĵ���;
'��Сд������,���ؽ��ΪСд��ʽ��
'����Ҫ���бȽ�ʱ��ͬ��ת����Сд��ʽ��
'**********************************************************
Function getSpellOfString(str)
	dim ret
	dim i
	for i=1 to len(str)
		cr=mid(str,i,1)
		if abs(asc(cr))<=255 then
			ret=ret & cr 'ֱ�ӷ���
		else
			ret=ret & getSpellOfChar(cr)
		end if
	next
	getSpellOfString=lcase(ret)	
end Function
