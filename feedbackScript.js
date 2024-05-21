function processFeedback()
{
	let userBasis = document.getElementsByName("usrBasis");
	let Basis;
	
	for(let i = 0; i < userBasis.length; i++)
	{
		if(userBasis[i].checked)
		{
			Basis = userBasis[i].value;
		}
	}
   let Name = document.getElementById("usrName").value;
   let Email = document.getElementById("usrEmail").value;
   let Rate = document.getElementById("usrResp" ).value;
   let Comment = document.getElementById("usrCom").value;
   let output = "Title:" + Basis + 
                "<br>Name: " + Name 
				+ "<br>Email Address: " + Email 
				+ "<br>Rate:" + Rate 
				+ "<br>Comments:" + Comment;
   document.write(output);
}