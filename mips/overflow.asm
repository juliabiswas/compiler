
# overflow: Prints the result of a multiplication (factors inputted by user) whose 
# 	    product is greater than the max integer value.
# @author Julia Biswas
# @version 23 April 2020

  .data
  	prompt:		.asciiz "Please type two numbers you would like to multiply: "
  .text
  .globl main
	
main:
  li $v0, 4			#asks the user to input the factors for the operation
  la $a0, prompt
  syscall
  
  li $v0, 5			#reads the user's input for the first number
  syscall
  move $t0, $v0
  
  li $v0, 5			#reads the user's input for the second number
  syscall
  move $t1, $v0
  
  mult $t0, $t1			#multiplies the two numbers
  
  mflo $t2			#retrieves the lo and hi values
  mfhi $t3
  
  li $t0, 2			#setting a variable to 2
  li $t5, 1		        #extra one at the end so that ending 0's don't get cut off
 
binaryhi:
  ble $t3, 0 shifttot9		#if $t3 (hi) is 0 or negative		 
  
  div $t3, $t0
  mfhi $t4			#remainder
  mflo $t3			#moves the quotient into $t3
  
  mul $t5, $t5, 10		#storing the binary value
  add $t5, $t5, $t4
  
  bgt $t5, 214748364 shifttot9  #ensuring that computing the next binary digit
  				#won't overflow
  				
  j binaryhi
  				
shifttot9:
  bgt $t9, 214748364 shifttot8
  
  move $t9, $t5			#moving $t5's contents to $t9
  li $t5, 1			#resetting $t5
  
  bgt $t3, 0 binaryhi		#determining where to go after
  ble $t3, 0 prep
  
shifttot8:
  bgt $t8, 214748364 shifttot7
  
  move $t8, $t5			#moving $t5's contents to $t8
  li $t5, 1			#resetting $t5
  
  bgt $t3, 0 binaryhi		#determining where to go after
  ble $t3, 0 prep
  
shifttot7:
  bgt $t7, 214748364 shifttot6
  
  move $t7, $t5			#moving $t5's contents to $t7
  li $t5, 1			#resetting $t5
  
  bgt $t3, 0 binaryhi		#determining where to go after
  ble $t3, 0 prep

shifttot6:
  move $t6, $t5			#moving $t5's contents to $t6
  li $t5, 1			#resetting $t5
  
  bgt $t3, 0 binaryhi		#determining where to go after
  ble $t3, 0 prep
  
prep:
  mul $t5, $t5, 10		#shifting place values over by one place **2x check if u have to do this
  add $t5, $t5, 0
  
  bge $t3, 0 binaryhi		#determining where to go after
  ble $t3, 0 prep
  
  #skip the first 1 (for all of the resgiters) & skip the ending 0's**** (should only be one but idk)
  #ble $t5, 0 decimallo (THIS IS GONNA GET CUT OFF THO BC U HAVE SHIFTTO9 ABOVE)
  
  
decimalhi:
  
  
  
    
 
decimallo:
  
    
after:
  li $v0, 10			#normal termination
  syscall
