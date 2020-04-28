  .data
  	prompt:		.asciiz "Please type a number: "
  .text
  .globl main
	
main:
  li $v0, 4			#asks for user input
  la $a0, prompt
  syscall
  
  li $v0, 5			#reads the user's input for the number
  syscall
  move $t0, $v0
  
  li $t1, 2
  li $t5, 1			#extra one at the end so that ending 0's don't get cut off
 
binaryhi:
  ble $t0, 0 after		#if $t0 (hi) is 0 or negative		 
  
  div $t0, $t1
  mfhi $t4			#remainder
  mflo $t0			#moves the quotient into $t0
  
  mul $t5, $t5, 10		#storing the binary value (backwards)
  add $t5, $t5, $t4
  				
  j binaryhi
  
after:
  #**DON'T CALCULATE FOR THE FIRST DIGIT (LIKE SKIP IT BC IT'S A ONE FOR EVERYONE & CLACULATE THE REST AFTER --> GOES FROM 2^0 AND INCREASES)
  li $v0, 10			#normal termination
  syscall