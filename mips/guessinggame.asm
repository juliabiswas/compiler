
# guessinggame: Determines a random number between 1 and 100 (inclusive) and 
# 		has the user guess values. If the guess is too low or too
#		high, the program will let the user know. Once the user
# 		has guessed the correct number, they will be congratulated,
# 		and the program will terminate.
# @author Julia Biswas
# @version 15 April 2020

  .data
  	prompt:		.asciiz	"Guess a number between 1 and 100 (inclusive): "
 	bigger:		.asciiz "Your guess was too big. Guess lower! \n"
 	smaller:  	.asciiz "Your guess was too small. Guess higher! \n"
 	correct: 	.asciiz "Congratulations! Your guess was correct! :) \n"
  .text
  .globl main
	
main:
  li $a1, 101		#generating a random number between 0 and 100 (inclusive)
  li $v0, 42
  syscall
 
  addu $t0, $a0, 1	#adds 1 to random number to make range 1 to 100 (inclusive),
  syscall		#stores random number in $t0
 
guess:
  li $v0, 4		#prints the prompt
  la $a0, prompt
  syscall
 
  li $v0, 5 		#reading user's input
  syscall
 
  bgt $v0, $t0, greater	#if guess is greater than actual
 
  blt $v0, $t0, less 	#if guess is less than actual
 
  li $v0, 4		#if guess is correct 
  la $a0, correct
  syscall
 
  li $v0, 10		#normal termination
  syscall
 
greater:
  li $v0, 4		#prints the "greater" message
  la $a0, bigger
  syscall
 
  j guess		#goes back to guessing
 
less:
  li $v0, 4		#prints the "less" message
  la $a0, smaller 
  syscall
 
  j guess		#goes back to guessing
 
 
 
 
