
# evenorodd: Prints 'Even' if the inputted number is even or 'Odd' if it is odd.
# @author Julia Biswas
# @version 22 April 2020

  .data
 	prompt:			.asciiz	"Enter a number: \n"
 	oddMessage: 		.asciiz "Odd"
 	evenMessage:		.asciiz "Even"
  .text
  .globl main
 
main:
  li $v0, 4		#prints the prompt
  la $a0, prompt
  syscall
  
  li $v0, 5		#reads the number the user inputs
  syscall
  move $t0, $v0
  
  div $t0, $t0, 2	#determines whether or not the number is even
  mfhi $t1
  
  beq $t1, 0 even	#if the number is even
  
  j odd			#if the number is odd

even:
  li $v0, 4		#prints "Even"
  la $a0, evenMessage
  syscall
  
  j after
  
odd:
  li $v0, 4		#prints "Odd"
  la $a0, oddMessage
  syscall
  
  #flows to after
  
after:
  li $v0, 10		#normal termination
  syscall

