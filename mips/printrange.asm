
# printrange: Prints all the numbers within a certain range, given
# 	      an inputted low (num >=), high (num <), and step.
# @author Julia Biswas
# @version 22 April 2020

  .data
  	prompt:		.asciiz	"Print low, high, and step values: "
  	switching:	.asciiz "Low value is greater than high value, so they will be switched.\n"
  	space: 		.asciiz	" "
  .text
  .globl main
	
main:
  li $v0, 4			#prints the prompt
  la $a0, prompt
  syscall
  
  li $v0, 5 			#reading user's low
  syscall
  move $t0, $v0
  
  li $v0, 5 			#reading user's high
  syscall
  move $t1, $v0
  
  li $v0, 5 			#reading user's step
  syscall
  move $t2, $v0
  
  bgt $t0, $t1 switch		#reverses low and high if low>high
  
  move $t3, $t0 		#setting current # to the low value
  
switch:
  li $v0, 4			#lets the user know that low and high will be switched
  la $a0, switching
  syscall
  
  move $t3, $t0			#switches t0 (low) and t1 (high)
  move $t0, $t1
  move $t1, $t3
  
  move $t3, $t0			#setting current # to the low value
  
looptest:
  bge $t3, $t1 endloop		#goes to end loop if current >= high
  
  li $v0, 1			#prints out the number
  move $a0, $t3
  syscall
  
  li $v0, 4			#prints a space
  la $a0, space
  syscall
  
  add $t3, $t3, $t2		#adds the step to the current number
  
  j looptest
  
endloop:
  li $v0, 10			#normal termination
  syscall
