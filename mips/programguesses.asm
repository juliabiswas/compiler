
# programguesses: User thinks of a number between 1 and 100 (inclusive), 
# 		  and the program tries to guess the number using hints
# 	          from the user which are -1 for too low, 1 for too high,
# 		  or 0 for correct. When the program finds the correct
#		  answer, it'll terminate.
# @author Julia Biswas
# @version 22 April 2020

  .data
  	prompt:		.asciiz "Think of a number between 1 and 100 (inclusive). Type 0 when you are ready. \n"
  	check:	 	.asciiz	"Is the number too low (-1), too high (1), or just right (0)? \n"
  	newline:        .asciiz "\n"
  .text
  .globl main
	
main:
  li $v0, 4		#asks the user to think of a number between 1 and 100 (inclusive)
  la $a0, prompt
  syscall
  
  li $v0, 5		#checks the user's input to see if they are ready
  syscall
  move $t7, $v0
  
  bne $t7, 0 main	#goes back to the start if the user is not ready (didn't type 0)	
  
  li $t0, 1		#setting low
  li $t1, 100		#setting high
  li $t4, 100		#setting high-low+1
  
  j rand
  
rand:
  move $a1, $t4		#generating a random number between 0 and high-low (inclusive)
  li $v0, 42
  syscall
 
  addu $t2, $a0, $t0	#adds low to random number and stores it in $t2
  
  li $v0, 1		#prints the guess
  move $a0, $t2
  syscall
  
  li $v0, 4		#prints a new line
  la $a0, newline
  syscall
  
  li $v0, 4		#asks the user to check the number
  la $a0, check
  syscall
  
  li $v0, 5		#reads the user's input & stores it in $t3
  syscall
  move $t3, $v0
  
  beq $t3, -1 toolow	#number is too low
  beq $t3, 1 toohigh	#number is too high
  beq $t3, 0 after	#number is right

toolow:
  add $t2, $t2, 1	#makes the low the guess+1
  move $t0, $t2
  
  sub $t4, $t1, $t0 	#setting high-low+1
  add $t4, $t4, 1	
  
  j rand
  
toohigh:
  sub $t2, $t2, 1	#makes the high the guess-1
  move $t1, $t2
  
  sub $t4, $t1, $t0 	#setting high-low+1
  add $t4, $t4, 1
  
  j rand
  
after:
  li $v0, 10		#terminates the program
  syscall
  
  
  
