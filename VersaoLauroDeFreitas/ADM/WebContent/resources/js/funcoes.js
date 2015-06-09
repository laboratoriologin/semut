var win = null;

function NewWindow(mypage,myname,l,t,w,h,s,r){
    settings ='height='+h+',width='+w+',top='+t+',left='+l+',scrollbars='+s+',resizable='+r+',status=no';
    win = window.open(mypage,myname,settings)
}

/*Fun??o  Pai de Mascaras*/
function Mascara(o,f){
    v_obj=o
    v_fun=f
    setTimeout("execmascara()",1)
}

function larguraTela() {
  return screen.width;
}

function alturaTela() {
  return screen.height;
}

/*Fun??o que Executa os objetos*/
function execmascara(){
    v_obj.value=v_fun(v_obj.value)
}

/*Fun??o que Determina as express?es regulares dos objetos*/
function leech(v){
    v=v.replace(/o/gi,"0")
    v=v.replace(/i/gi,"1")
    v=v.replace(/z/gi,"2")
    v=v.replace(/e/gi,"3")
    v=v.replace(/a/gi,"4")
    v=v.replace(/s/gi,"5")
    v=v.replace(/t/gi,"7")
    return v
}

/*Fun??o que permite apenas numeros*/
function Integer(v){
    return v.replace(/\D/g,"")
}

/*Fun??o que padroniza telefone (11) 4184-1241*/
function Telefone(v){
    v=v.replace(/\D/g,"")                 
    v=v.replace(/^(\d\d)(\d)/g,"($1) $2") 
    v=v.replace(/(\d{4})(\d)/,"$1-$2")    
    return v
}

/*Fun??o que padroniza telefone (11) 41841241*/
function TelefoneCall(v){
    v=v.replace(/\D/g,"")                 
    v=v.replace(/^(\d\d)(\d)/g,"($1) $2")    
    return v
}

/*Fun??o que padroniza CPF*/
function Cpf(v){
    v=v.replace(/\D/g,"")                    
    v=v.replace(/(\d{3})(\d)/,"$1.$2")       
    v=v.replace(/(\d{3})(\d)/,"$1.$2")       
                                             
    v=v.replace(/(\d{3})(\d{1,2})$/,"$1-$2") 
    return v
}

/*Fun??o que padroniza CEP*/
function Cep(v){
    v=v.replace(/D/g,"")                
    v=v.replace(/^(\d{5})(\d)/,"$1-$2") 
    return v
}

/*Fun??o que padroniza CNPJ*/
function Cnpj(v){
    v=v.replace(/\D/g,"")                   
    v=v.replace(/^(\d{2})(\d)/,"$1.$2")     
    v=v.replace(/^(\d{2})\.(\d{3})(\d)/,"$1.$2.$3") 
    v=v.replace(/\.(\d{3})(\d)/,".$1/$2")           
    v=v.replace(/(\d{4})(\d)/,"$1-$2")              
    return v
}

/*Fun??o que permite apenas numeros Romanos*/
function Romanos(v){
    v=v.toUpperCase()             
    v=v.replace(/[^IVXLCDM]/g,"") 
    
    while(v.replace(/^M{0,4}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$/,"")!="")
        v=v.replace(/.$/,"")
    return v
}

/*Fun??o que padroniza o Site*/
function Site(v){
    v=v.replace(/^http:\/\/?/,"")
    dominio=v
    caminho=""
    if(v.indexOf("/")>-1)
        dominio=v.split("/")[0]
    caminho=v.replace(/[^\/]*/,"")
    dominio=dominio.replace(/[^\w\.\+-:@]/g,"")
    caminho=caminho.replace(/[^\w\d\+-@:\?&=%\(\)\.]/g,"")
    caminho=caminho.replace(/([\?&])=/,"$1")
    if(caminho!="")dominio=dominio.replace(/\.+$/,"")
    v="http://"+dominio+caminho
    return v
}

/*Fun??o que padroniza DATA*/
function Data(v){
    v=v.replace(/\D/g,"") 
    v=v.replace(/(\d{2})(\d)/,"$1/$2") 
    v=v.replace(/(\d{2})(\d)/,"$1/$2") 
    return v
}

/*Fun??o que padroniza DATA*/
function Hora(v){
    v=v.replace(/\D/g,"") 
    v=v.replace(/(\d{2})(\d)/,"$1:$2")  
    return v
}

/*Fun??o que padroniza valor mon?tario*/
function Valor(v){
    v=v.replace(/\D/g,"") //Remove tudo o que n?o ? d?gito
    v=v.replace(/^([0-9]{3}\.?){3}-[0-9]{2}$/,"$1.$2");
    //v=v.replace(/(\d{3})(\d)/g,"$1,$2")
    v=v.replace(/(\d)(\d{2})$/,"$1.$2") //Coloca ponto antes dos 2 ?ltimos digitos
    return v
}

/*Fun??o que padroniza Area*/
function Area(v){
    v=v.replace(/\D/g,"") 
    v=v.replace(/(\d)(\d{2})$/,"$1.$2") 
    return v
    
}

function mascaraMoeda(campo) {
    campo.value=MaskMonetario(campo.value);
}

function MaskMonetario(v){
    v=v.replace(/\D/g,""); //Remove tudo o que n?o ? d?gito
    v=v.replace(/(\d{2})$/,",$1"); //Coloca a virgula
    v=v.replace(/(\d+)(\d{3},\d{2})$/g,"$1.$2"); //Coloca o primeiro ponto
    var qtdLoop = (v.length-3)/3;
    var count = 0;
    while (qtdLoop > count)
    {
        count++;
        v=v.replace(/(\d+)(\d{3}.*)/,"$1.$2"); //Coloca o resto dos pontos
    }
    v=v.replace(/^(0)(\d)/g,"$2"); //Coloca h?fen entre o quarto e o quinto d?gitos
    return v;
}

//Limita a quantidade de caracteres num textarea
function validarQtdeCaracteres(valor, max)     
{     	        
    if(valor.value.length < max){

        return true;   

    }

    alert('Insira no máximo ' + max + ' caracteres.'); 

    window.event.returnValue=false;            
      
    return false;     
}

function textCounter(field,maxlimit) {
    if (field.value.length > maxlimit)
        field.value = field.value.substring(0, maxlimit);
                    
}

function mac(v){
    v=v.toUpperCase() //Maiúsculas
    v=v.replace(/[^ABCDEFG0123456789]/g,"") //Remove tudo o que não for A, B, C, D, E, F, G ou Numeros    
    v = v.replace(/(\w\w{1})(\w{12})$/, "$1:$2");
    v = v.replace(/(\w\w{1})(\w{10})$/, "$1:$2");
    v = v.replace(/(\w\w{1})(\w{8})$/, "$1:$2");
    v = v.replace(/(\w\w{1})(\w{6})$/, "$1:$2");
    v = v.replace(/(\w\w{1})(\w{4})$/, "$1:$2");
    v = v.replace(/(\w\w{1})(\w{2})$/, "$1:$2");
    return v;
}

function complexMoney(v) {
	
	v=v.replace(/[^\d\,]+/g,"");
	if(v.indexOf(',')!=v.lastIndexOf(',')) {
    	v=v.replace(/,([^,]*)$/,'$1'); 
    }
	
	if(v.indexOf(',')>0 && v.length-v.indexOf(',')>5) {
		v=v.substring(0,v.length-1);
	}
    
    return v;
	
}

PrimeFaces.locales['pt_br'] = {
        closeText: 'Fechar',
        prevText: 'Anterior',
        nextText: 'Próximo',
        currentText: 'Começo',
        monthNames: ['Janeiro','Fevereiro','Março','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
        monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun', 'Jul','Ago','Set','Out','Nov','Dez'],
        dayNames: ['Domingo','Segunda','Terça','Quarta','Quinta','Sexta','Sábado'],
        dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','Sáb'],
        dayNamesMin: ['D','S','T','Q','Q','S','S'],
        weekHeader: 'Semana',
        firstDay: 1,
        isRTL: false,
        showMonthAfterYear: false,
        yearSuffix: '',
        timeOnlyTitle: 'Só Horas',
        timeText: 'Tempo',
        hourText: 'Hora',
        minuteText: 'Minuto',
        secondText: 'Segundo',
        currentText: 'Data Atual',
        ampm: false,
        month: 'Mês',
        week: 'Semana',
        day: 'Dia',
        allDayText : 'Todo Dia'
    };

PrimeFaces.locales['pt_BR'] = {
        closeText: 'Fechar',
        prevText: 'Anterior',
        nextText: 'Próximo',
        currentText: 'Começo',
        monthNames: ['Janeiro','Fevereiro','Março','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
        monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun', 'Jul','Ago','Set','Out','Nov','Dez'],
        dayNames: ['Domingo','Segunda','Terça','Quarta','Quinta','Sexta','Sábado'],
        dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','Sáb'],
        dayNamesMin: ['D','S','T','Q','Q','S','S'],
        weekHeader: 'Semana',
        firstDay: 1,
        isRTL: false,
        showMonthAfterYear: false,
        yearSuffix: '',
        timeOnlyTitle: 'Só Horas',
        timeText: 'Tempo',
        hourText: 'Hora',
        minuteText: 'Minuto',
        secondText: 'Segundo',
        currentText: 'Agora',
        ampm: false,
        month: 'Mês',
        week: 'Semana',
        day: 'Dia',
        allDayText : 'Todo Dia'
    };


function isEmpty(campos){

	var aux = false;
	var retorno = false;
	
	for(i=0;i < campos.length;i++){
	
	    if ($(campos[i]).val().trim() == ""){
	        $(campos[i]).css('border-color', 'red');
	        aux = true;
	    }else{
	        $(campos[i]).css('border-color', 'green');
	        aux = false;
	    }
	
	    if (aux == true){
	        retorno = true;
	    }
	
	}
	
	return retorno;
}


function handleRequest(widgerVar, args) {
	if(args.valido) {      			  
		widgerVar.hide();
	}

}


    
  