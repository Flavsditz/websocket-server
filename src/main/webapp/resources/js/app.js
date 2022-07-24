let stompClient = null;

function setConnected(connected) {
    document.getElementById('connect').disabled = connected;
    document.getElementById('conversationDiv').style.display = connected ? '' : 'none';
    document.getElementById('loginWrapper').style.display = connected ? 'none' : '';
    document.getElementById('responseArea').innerHTML = '';
}

function connect() {
    let userName = getUserName();

    if (userName === "") {
        document.getElementById('errorMessage').innerText = 'The name cannot be empty';
        return;
    } else {
        document.getElementById('errorMessage').innerText = '';
    }

    const socket = new SockJS('/websocket-chat');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {
        setConnected(true);

        console.log('Connected: ' + frame);

        stompClient.subscribe('/topic/messages', function (messageOutput) {
            showMessageOutput(JSON.parse(messageOutput.body));
        });

        sendJoinAnnouncement();

        stompClient.subscribe('/topic/userJoined', function (messageOutput) {
            showServerMessageOutput(JSON.parse(messageOutput.body));
        });
        stompClient.subscribe('/topic/userLeft', function (messageOutput) {
            showServerMessageOutput(JSON.parse(messageOutput.body));
        });
    });
}

function getUserName() {
    return document.getElementById('userName').value.trim();
}

function sendJoinAnnouncement() {
    const userName = getUserName();
    stompClient.send("/app/joined", {}, userName);
}

function disconnect() {
    if (stompClient != null) {
        const userName = getUserName();
        stompClient.send("/app/left", {}, userName);

        stompClient.disconnect();
    }

    setConnected(false);
    console.log("Disconnected");
}

function sendMessage() {
    const userName = getUserName();
    const text = document.getElementById('text').value;
    document.getElementById('text').value = ""

    if (text.trim() !== "") {
        stompClient.send("/app/chat", {}, JSON.stringify({'from': userName, 'text': text}));
    }
}

function showMessageOutput(messageOutput) {
    let newElement;

    const userName = getUserName();
    if (userName === messageOutput.from) {
        newElement = createDiv(['message', 'own']);
    } else {
        newElement = createDiv(['message', 'other']);
    }

    const authorEl = createDiv(['author'], messageOutput.from);
    newElement.appendChild(authorEl);

    const textEl = createDiv(['text'], messageOutput.text);
    newElement.appendChild(textEl);

    const timeEl = createDiv(['time'], messageOutput.time);
    newElement.appendChild(timeEl);


    addToResponses(newElement);
}

function showServerMessageOutput(messageOutput) {
    const newElement = createDiv(['message', 'server']);

    const textEl = createDiv(['text'], messageOutput.text);
    newElement.appendChild(textEl);

    const timeEl = createDiv(['time'], messageOutput.time);
    newElement.appendChild(timeEl);

    addToResponses(newElement);
}

function createDiv(classNames, text) {
    const element = document.createElement('div');
    classNames.forEach(name => element.classList.add(name))

    if (text) {
        element.innerText = text
    }
    return element;
}

function addToResponses(newElement) {
    const response = document.getElementById('responseArea');
    response.appendChild(newElement);

    // Scroll to bottom
    response.scrollTop = response.scrollHeight;
}
