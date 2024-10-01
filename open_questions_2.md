## Question 1 (Design a Simple Memory Manager)
You are tasked with implementing the following methods
```c
void* alloc(); // Allocates a block of memory of size 16KB
void free(void* ptr); // Frees the block of memory pointed to by ptr
```
You are given a block of memory pool of size 1MB of which the start of is indicated
by pointer P. Implement the above methods in a
way that maximizes memory utilization. You can use any data structure or algorithm to
implement the above methods.
You can assume that the memory pool is empty when the program starts.
Each block is 16KB in size. Consider the fact that each memory you use for managing the memory
will be taken from the block of memory pool itself.
For simplicity assume it is a single thread system.

#### Implementation #1
Using array of bytes. While byte is the smallest memory unit we can use directly, we can use each byte
to represent 8 bits. This way we can use 1 byte to represent 8 blocks of memory. This is 2^3 more efficient 
than using complete bytes.

##### Implement alloc()
1. How to determine if an entire cell in the byte array is full?
If the value of the byte is 255, then the cell is full.
2. How to determine the first free block in the cell (first free bit)?
We can use bitwise operations to determine the first free bit in the byte. This will require 7 iterations at most.
To determine if the first bit is free we can take the value of the byte and AND it with 1. If the result is 0, then the first bit is free.
To determine if the second bits is free we can take the value of the byte and AND it with 2. If the result is 0, then the second bit is free.
And so on.
So, to summarize, we can go over the byte array and use #1 to see if the cell is full. If it is not full, we can use #2 to determine the first free bit.
Once we know the index of the first free bit we add the index of the cell with the bit number
and multiply it by 16KB to get the address of the block relative to P, and adding P to it will give us the address of the block.

##### Implement free()
When a user wants to free a block it will give us the pointer to the block. Since the block of memory begins with pointer P,
we first need to subtract it from the pointer. This will give us the offset of the block from the start of the memory pool.
Dividing by 16KB will give us the index of the block in the memory pool. We can then use this index to determine the cell and bit number.
Now we need to set the desired bit to 0 in the byte array. If we want to free the bit at index i, we need to AND it with the complement of 2^i.

##### Calculating Efficiency
1. Time complexity is O(n) for alloc() and O(1) for free().
2. Space complexity is O(n) where n is number of blocks in the memory pool.
3. Since we want to represent 2^24/2^14 = 2^10 blocks, we need 2^10/8 = 2^7 bytes.
So, we need 128 bytes to represent the memory pool. So roughly 2^24 memory and 2^7 for management
means we use 2^7/2^24 = 1/2^17 = 1/131072 = 0.00000762939 of the memory for management.

#### Implementation #2 (Space and time complexity is O(1))
We can use the memory pool itself to represent of linked list of free blocks. We need a single variable
to keep track of the first free block. So we being with the variable containing 0. When we allocate 0 the next value
the variable will hold will be 1 and so on. Let's say we allocated all the blocks until block 5, which is the value in the variable.
And now someone asks to free block 2. We set the variable to 2 and set the value of block 2 to 5.
This way we can keep track of the free blocks.

## Question 2 (Design Word Search)
Implement 2 methods
1. ```suggest(prefix)``` which returns a list of words that start with the given prefix.
2. ```search(word)``` which returns the number of times the word has been searched.

#### Implementing suggest()
We can use a trie to store the words. When we are given a prefix
we can traverse the trie to the node that represents the prefix and return all subtrees. we can also save the amount
of times the word has been searched in the node where the word ends.

#### Implementing search()
Whenever we get a word to search we traverse the trie to the node that represents 
the word, increment the number of times the word has been searched and return the value.

#### Improving Performance
Since in order to return all the subtrees when we do suggest() we could maybe return a very large
number of words which might be unnecessary. So instead we can return only the top k words that were most
frequently searched. We can keep a priority queue of size k (heap) in each node and update the list
accordingly. This will also eliminate the need to traverse the entire trie to get the subtrees.

## Question 3 (Design a Harmful Content Detection System)
You are tasked with designing a system that can detect harmful content in a given text. You are give
multiple "AI" services that can work on an input to determine if it is harmful or not.
Each service can use has a model which is built to determine harmful content in a single category.

1. Build a synchronous system that can take a text as input and return if it is harmful or not 
and in which categories.
2. The same but now the input can be a media payload such as a video, image or audio build an
asynchronous system that can take a media payload as input and return if it is harmful or not and
in which categories.