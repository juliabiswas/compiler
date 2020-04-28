
# multiplication: Multiplies two inputted numbers and prints
# 		  their product.
# @author Julia Biswas
# @version 15 April 2020

 .data
 	prompt:		.asciiz	"Please enter two numbers: "
 .text

  li $v0, 4 		#asks the user for the two numbers
  la $a0, prompt
  syscall

  li $v0, 5 		#reading user's input for the first number
  syscall
  move $t0, $v0
  
  li $v0, 5 		#reading user's input for the second number
  syscall
  move $t1, $v0

  mul $a0, $t1, $t0	#multiply the two numbers & print the result
  li $v0, 1
  syscall
  
  li $v0, 10		#normal termination
  syscall
