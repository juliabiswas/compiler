
# helloworld: Prints the phrase "Hello World!"
# @author Ms. Datar
#         Julia Biswas
# @version 15 April 2020

  .data
	greeting:	.asciiz	"Hello World!"
  .text

  li $v0, 4		#prints "Hello World!"
  la $a0, greeting
  syscall

  li $v0, 10		#normal termination
  syscall
