from tag import Tag


def compression():
    text = input("Enter the text: ")
    tags = []
    i = 0
    while i < len(text):
        ok, position, length = 1, 0, 0
        while ok:
            if i + length + 1 >= len(text):
                break
            cur = text[i: i + length + 1]
            ok = 0
            for j in reversed(range(i - length)):
                if text[j: j + length + 1] == cur:
                    length += 1
                    position = j
                    ok = 1
                    break

        if length > 0:
            tags.append(Tag(int(i - position), int(length), str(text[i + length])))
        else:
            tags.append(Tag(0, 0, str(text[i + length])))

        i += length + 1

    print("The tags are: ")
    for tag in tags:
        print(f"< {tag.position}, {tag.length}, {tag.nextChar} >")


def decompression():
    numberOfTags = int(input("Enter the number of tags: "))
    tags = []
    for i in range(numberOfTags):
        position, length, nextChar = input().split()
        tags.append(Tag(int(position), int(length), str(nextChar)))

    collected = ""
    for tag in tags:
        position = tag.position
        length = tag.length
        nextChar = tag.nextChar
        for i in range(len(collected) - int(position), len(collected) - int(position) + int(length), 1):
            collected += collected[i]
        collected += nextChar

    print(f"The result is: {collected}")


def main_menu():
    while True:
        print("\nMenu:")
        print("1. Compression")
        print("2. Decompression")
        print("3. Exit")

        choice = input("Enter your choice: ")

        if choice == '1':
            compression()

        elif choice == '2':
            decompression()

        elif choice == '3':
            print("Exiting the program.")
            break
        else:
            print("Invalid choice, Please try again.")


# Example: <0, 0, A> <0, 0, B> <2, 1, A> <3, 2, B> <5, 3, B> <2, 2, B> <5, 5, B> <1, 1, A>
# Expected result for this example is: ABAABABAABBBBBBBBBBBBA
main_menu()